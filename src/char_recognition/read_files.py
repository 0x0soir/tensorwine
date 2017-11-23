import glob, os
import array
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'

class File:
    def __init__(self, direction):
        self.dir = direction
        file_array = self.dir.split(os.sep)
        self.folder = file_array[len(file_array)-2]
        self.name = file_array[len(file_array)-1]

class Folder:
    def __init__(self, folder):
        self.name = folder
        self.files = []

    def __str__(self):
        return "{} {}".format(self.name, self.files)

files = []
folders = []

for file in glob.glob('../build_font_database/'+os.sep+'images'+os.sep+'*'+os.sep+'*.png'):
    f = File(file)
    #print("{} {}".format(f.folder, f.name))
    files.append(f)
    contains = False
    for subf in folders:
        if subf.name == f.folder:
            contains = True
            subf.files.append(f)
    if contains == False:
        fold = Folder(f.folder)
        folders.append(fold)
        fold.files.append(f)

for file in glob.glob('../build_font_database/'+os.sep+'Otras'+os.sep+'*.png'):
    f = File(file)
    #print("{} {}".format(f.folder, f.name))
    files.append(f)
    contains = False
    for subf in folders:
        if subf.name == f.folder:
            contains = True
            subf.files.append(f)
    if contains == False:
        fold = Folder(f.folder)
        folders.append(fold)
        fold.files.append(f)
