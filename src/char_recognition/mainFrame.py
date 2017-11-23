import wx
import panelDatos as pd
import treePanel as tree
import read_files as data

class MainFrame(wx.Frame):

    def __init__(self, net):
        wx.Frame.__init__(self, parent=None, title='Visor de imágenes')
        self.net = net
        panel = pd.PanelDatos(self)
        panel2 = tree.TreePanel(self, panel, net)

        for folder in data.folders:
            panel2.nuevaCarpeta(folder.name)

        for fichero in data.files:
            panel2.nuevoFichero(fichero.folder,fichero.name)

        vbox = wx.BoxSizer(wx.VERTICAL)
        gs = wx.GridSizer(2, 1, 5, 5)

        gs.AddMany( [(panel2, 0, wx.EXPAND),
        (panel, 0, wx.EXPAND) ])

        vbox.Add(gs, proportion=1, flag=wx.EXPAND)
        self.SetSizer(vbox)

        self.Show()
