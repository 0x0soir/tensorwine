import tensorflow as tf
import sys, os
import numpy as np

os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3'

class Wine_Predict:
    __graph_dir = ''
    __labels_dir = ''

    def __init__(self, graph_dir, labels_dir):
        self.__graph_dir = graph_dir # Modelo
        self.__labels_dir = labels_dir # Etiquetas

    def predict(self, image_route):
        labels = [line.rstrip() for line in tf.gfile.GFile(self.__labels_dir)]

        with tf.gfile.FastGFile(self.__graph_dir, 'rb') as fp:
            graph_def = tf.GraphDef()
            graph_def.ParseFromString(fp.read())
            tf.import_graph_def(graph_def, name='')

        with tf.Session() as sess:
            logits = sess.graph.get_tensor_by_name('final_result:0')
            image = tf.gfile.FastGFile(image_route, 'rb').read()
            prediction = sess.run(logits, {'DecodeJpeg/contents:0': image})

        #print('=== Más probable ===')
        top_result = int(np.argmax(prediction[0]))
        name = labels[top_result]
        score = prediction[0][top_result]
        #print('%s (%.2f%%)' % (name, score * 100))

        # print('=== Probabilidades ===')
        # for i in range(len(labels)):
        #     name = labels[i]
        #     score = prediction[0][i]
        #     print('%s (%.2f%%)' % (name, score * 100))

        return {'wine_name': name, 'wine_score': score*100}
