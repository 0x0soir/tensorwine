from wine_predict import *
import colorama
from colorama import *

predict_class = Wine_Predict('model_info/wine_graph.pb', 'model_info/wine_labels.txt')

def test_image(route, label):
    predicted_label = predict_class.predict(route)
    if predict_class.predict(route) == label:
        print("Probando '%s' \t['%s', '%s'] %s" % (route, label, predicted_label, Fore.GREEN + '[OK]' + Style.RESET_ALL))
    else:
        print("Probando '%s' \t['%s', '%s'] %s" % (route, label, predicted_label, Fore.RED + '[ERROR]' + Style.RESET_ALL))

test_image('test_images/bombay.jpg', 'bombay saphire')
test_image('test_images/tilenus.jpg', 'tilenus')
test_image('test_images/seagrams.jpg', 'seagrams')
test_image('test_images/gordons.jpg', 'gordons')
