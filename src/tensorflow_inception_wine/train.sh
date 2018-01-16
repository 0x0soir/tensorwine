# Train Wine model

python3 train_model.py --bottleneck_dir=./model_info/bottlenecks --model_dir=./model_info/inception --output_graph=./model_info/wine_graph.pb --output_labels=./model_info/wine_labels.txt --image_dir ./model_info/images --how_many_training_steps 2000
