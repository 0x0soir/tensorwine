from html.parser import HTMLParser
from urllib.request import urlopen
from urllib import parse
from bs4 import BeautifulSoup
import json, io

try:
    to_unicode = unicode
except NameError:
    to_unicode = str

data = []

def get_page_data_products(htmlString):
    soup = BeautifulSoup(htmlString, "html5lib")

    product_table = soup.find_all(class_="product-view-2")

    if(len(product_table)>0):
        filter_results_trs = BeautifulSoup(str(product_table[0]), "html5lib")

        filter_results_trs_individuals = filter_results_trs.find_all('tr')

        for wine in filter_results_trs_individuals:
            wine_not_fildered = BeautifulSoup(str(wine), "html5lib")
            wine_fildered_info = wine_not_fildered.find_all(class_='info')

            wine_fildered_info_not = BeautifulSoup(str(wine_fildered_info), "html5lib")
            wine_fildered_info_name2 = wine_fildered_info_not.find_all('h3')

            wine_fildered_info_not2 = BeautifulSoup(str(wine_fildered_info_name2), "html5lib")
            wine_fildered_info_name = wine_fildered_info_not2.find_all('a')

            wine_fildered_info_not_price = BeautifulSoup(str(wine_fildered_info), "html5lib")
            wine_fildered_info_price = wine_fildered_info_not_price.find_all(class_='price')

            wine_fildered_info_not_type = BeautifulSoup(str(wine_fildered_info), "html5lib")
            wine_fildered_info_type = wine_fildered_info_not_type.find_all(class_='type')

            wine_fildered_info_not_owner = BeautifulSoup(str(wine_fildered_info), "html5lib")
            wine_fildered_info_owner = wine_fildered_info_not_owner.find_all(class_='owner')

            wine_fildered_info_not_grape = BeautifulSoup(str(wine_fildered_info), "html5lib")
            wine_fildered_info_grape = wine_fildered_info_not_grape.find_all(class_='grape')

            wine_points_not_fildered = BeautifulSoup(str(wine), "html5lib")
            wine_fildered_points = wine_points_not_fildered.find(height='16')

            wine_json = {
                'name': str(wine_fildered_info_name[0].contents[0]),
                'price': str(wine_fildered_info_price[0].contents[0]).strip() if len(wine_fildered_info_price) > 0 else None,
                'type': str(wine_fildered_info_type[0].contents[0]).strip(),
                'owner': str(wine_fildered_info_owner[0].contents[0]).strip(),
                'grape': "".join(str(wine_fildered_info_grape[0].contents[0]).split()),
                'points': wine_fildered_points['title'] if wine_fildered_points != None else None
            }

            data.append(wine_json)
            pass

def get_page_data(url):

    print("Abriendo URL: "+url)

    response = urlopen(url)

    if response.getheader('Content-Type')=='text/html' or response.getheader('Content-Type')=='text/html; charset=utf-8':
        htmlBytes = response.read()
        htmlString = htmlBytes.decode("utf-8")

        soup = BeautifulSoup(htmlString, "html5lib")

        filter_results = soup.find_all(class_="filter-results")

        if(len(filter_results)>0):
            filter_results_spans = BeautifulSoup(str(filter_results[0]), "html5lib")

            filter_results_spans_individuals = filter_results_spans.find_all('span')

            first_wine = filter_results_spans_individuals[0].contents[0]
            last_wine = filter_results_spans_individuals[1].contents[0]
            total_wines = filter_results_spans_individuals[2].contents[0]

            print("## First wine: "+first_wine+" Last wine: "+last_wine+" Total wines: "+total_wines)

            if (int(first_wine)>0):
                get_page_data_products(htmlString)

            if (int(last_wine) < int(total_wines)):
                get_page_data('https://www.vinissimus.com/es/vinos/regiones/index.html?id_pais=es&start='+last_wine)
            else:
                print("ACABA: "+last_wine+" - "+total_wines)
    else:
        print("Recibido con respuesta "+response.getheader('Content-Type'))

baseUrl = 'https://www.vinissimus.com/es/vinos/regiones/?id_pais=es'

get_page_data(baseUrl)

with io.open('data.json', 'w', encoding='utf8') as outfile:
    str_ = json.dumps(data,
                      indent=4, sort_keys=True,
                      separators=(',', ': '), ensure_ascii=False)
    outfile.write(to_unicode(str_))
