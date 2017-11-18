import tensorflow as tf
import glob, os

os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'

nombre = []

for file in glob.glob('.'+os.sep+'images'+os.sep+'*'+os.sep+'*.png'):
    nombre.append(file)

for i in range(len(nombre)):
    filename_queue = tf.train.string_input_producer([nombre[i]]) #  list of files to read

    reader = tf.WholeFileReader()
    key, value = reader.read(filename_queue)

    my_img = tf.image.decode_png(value) # use png or jpg decoder based on your files.

init_op = tf.global_variables_initializer()
with tf.Session() as sess:
    sess.run(init_op)

    # Start populating the filename queue.
    coord = tf.train.Coordinator()
    hreads = tf.train.start_queue_runners(coord=coord)

    for i in range(len(nombre)): #length of your filename list
        image = my_img.eval() #here is your image Tensor :)

        print("Imagen: ", image)