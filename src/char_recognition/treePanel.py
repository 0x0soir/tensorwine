import wx
import numpy
import read_files as data
from PIL import Image
import letter

class MyTree(wx.TreeCtrl):

    def __init__(self, parent, id, pos, size, style):
        wx.TreeCtrl.__init__(self, parent, id, pos, size, style)

class TreePanel(wx.Panel):

    def __init__(self, parent, panel, net):
        wx.Panel.__init__(self, parent)
        self.panel = panel
        self.net = net

        self.tree = MyTree(self, wx.ID_ANY, wx.DefaultPosition, wx.DefaultSize, wx.TR_HAS_BUTTONS)
        self.carpetas = []
        self.nombres = []
        self.ficheros = []
        self.nombresFiles = []

        self.Bind(wx.EVT_TREE_SEL_CHANGING, self.OnClick, self.tree)
        self.root = self.tree.AddRoot('Images')
        self.tree.Expand(self.root)

        sizer = wx.BoxSizer(wx.VERTICAL)
        sizer.Add(self.tree, 1, wx.EXPAND)
        self.SetSizer(sizer)

    def OnClick(self, e):
        try:
            index = self.ficheros.index(e.GetItem())
            self.panel.etiqueta.SetLabel(self.nombresFiles[index])
            self.panel.ruta.SetLabel(data.files[index].dir)
            l1, l2, l3, e1, e2, e3 = self.predict(data.files[index].dir)
            self.panel.generada1.SetLabel(l1)
            self.panel.generada2.SetLabel(l2)
            self.panel.generada3.SetLabel(l3)
            self.panel.error1.SetLabel(str(e1))
            self.panel.error2.SetLabel(str(e2))
            self.panel.error3.SetLabel(str(e3))
        except ValueError:
            value = False

    def nuevaCarpeta(self, nombre):
        self.carpetas.append(self.tree.AppendItem(self.root, nombre))
        self.nombres.append(nombre)

    def nuevoFichero(self, carpeta, fichero):
        index = self.nombres.index(carpeta)
        self.ficheros.append(self.tree.AppendItem(self.carpetas[index], fichero))
        self.nombresFiles.append(fichero)

    def predict(self, direction):
    	size = 24  # 24 pycharm font
    	image = Image.open(direction)
    	#print(image)
    	array = numpy.array(image.getdata()) # (1, 4000, 4)
    	mat = array.reshape(image.height, image.width, 4)[:, :, 0]
    	if size> image.height:
    		mat=numpy.pad(mat, (0,  size- image.height), 'constant', constant_values=255) # 1==white!
    	mat = 1 - 2 * mat / 255.
    	try:
    		result = self.net.predict(mat)
    		estimada = numpy.argmax(result)
    		list_sort = numpy.argsort(result)
    		size=len(list_sort[0][0])
    		best=estimada+letter.offset
    		best1 = result[0][0][size-1]+letter.offset
    		best2 = result[0][0][size-2]+letter.offset
    		best3 = result[0][0][size-3]+letter.offset
    		letra1 = chr(list_sort[0][0][size-1]+letter.offset)
    		letra2 = chr(list_sort[0][0][size-2]+letter.offset)
    		letra3 = chr(list_sort[0][0][size-3]+letter.offset)
    		error1 = result[0][0][size-1]
    		error2 = result[0][0][size-2]
    		error3 = result[0][0][size-3]
    		return [letra1, letra2, letra3, error1, error2, error3]
    	except Exception as ex:
    		print("EX: %s"%ex)
