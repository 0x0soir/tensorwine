import json, imp
from django.conf import settings
from django.http import HttpResponseForbidden, JsonResponse, HttpResponse
from django.views.decorators.csrf import csrf_exempt, csrf_protect
from django.core.files.storage import FileSystemStorage

foo = imp.load_source('*', '../../tensorflow_inception_wine/wine_predict.py')

###################
# PRIVATE METHODS #
###################
def __neural_network(route):
    predict_class = foo.Wine_Predict('../../tensorflow_inception_wine/model_info/wine_graph.pb', '../../tensorflow_inception_wine/model_info/wine_labels.txt')

    predicted_label = predict_class.predict(route)

    return { 'status': True, 'predicted_label': predicted_label }

def __get_wine_info(name):
    json_data=open('../../build_wine_database/data.json').read()

    data = json.loads(json_data)

    for i in data:
        if (i['name'].upper() == name.upper()):
            return i

    return {}

###################
#  PUBLIC METHODS #
###################
def index(request):
    return JsonResponse({'status':'error'})

@csrf_exempt
def recognize_photo(request):
    print(settings.MEDIA_ROOT)
    if request.method == 'POST':
        wine_photo = request.FILES['wine_photo']

        if (wine_photo):
            fs = FileSystemStorage()
            filename = fs.save(wine_photo.name, wine_photo)
            uploaded_file_url = fs.path(filename)

            response = __neural_network(uploaded_file_url)

            html_response = {}

            if (response['status']) or (float(response['predicted_score'])>=50):
                html_response['status'] = 'match'
                html_response['wine_info'] = {
                    'predicted_wine': response['predicted_label']['wine_name'],
                    'predicted_score': response['predicted_label']['wine_score'],
                    'wine_info': __get_wine_info(response['predicted_label']['wine_name'])
                }
            else:
                html_response['status'] = 'error'

            return JsonResponse(html_response)

        return JsonResponse({'status':'error', 'reason': 'Error subiendo imagen'})
    return JsonResponse({'status':'error', 'reason': 'allowed only via POST'})
