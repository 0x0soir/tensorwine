from wine_predict import *
import colorama
from colorama import *

predict_class = Wine_Predict('model_info/wine_graph.pb', 'model_info/wine_labels.txt')

def test_image(route, label):
    predicted_label = predict_class.predict(route)
    if predicted_label['wine_name'] == label:
        print("Probando '%s' \t['%s', '%s'] %s" % (route, label, predicted_label['wine_name'], Fore.GREEN + '[OK]' + Style.RESET_ALL))
    else:
        print("Probando '%s' \t['%s', '%s'] %s" % (route, label, predicted_label['wine_name'], Fore.RED + '[ERROR]' + Style.RESET_ALL))

test_image('test_images/dominio.png', 'ea956c062eee9ae092b9408509ae147f')
test_image('test_images/bembibre.png', 'bembibre')
test_image('test_images/pinord.png', '50ab1a4b3e76681e90f60b67fe85ef55')
