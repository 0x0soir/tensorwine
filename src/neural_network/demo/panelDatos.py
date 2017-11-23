import wx
class PanelDatos(wx.Panel):
    def __init__(self, parent):
        wx.Panel.__init__(self, parent)

        self.etiqueta = wx.StaticText(self, label='', style=wx.ALIGN_CENTER)
        self.ruta = wx.StaticText(self, label='', style=wx.ALIGN_CENTER)
        self.generada1 = wx.StaticText(self, label='', style=wx.ALIGN_CENTER)
        self.generada2 = wx.StaticText(self, label='', style=wx.ALIGN_CENTER)
        self.generada3 = wx.StaticText(self, label='', style=wx.ALIGN_CENTER)
        self.error1 = wx.StaticText(self, label='', style=wx.ALIGN_CENTER)
        self.error2 = wx.StaticText(self, label='', style=wx.ALIGN_CENTER)
        self.error3 = wx.StaticText(self, label='', style=wx.ALIGN_CENTER)

        vbox = wx.BoxSizer(wx.VERTICAL)
        gs = wx.GridSizer(8, 2, 5, 5)

        gs.AddMany( [(wx.StaticText(self, label='Etiqueta de la imagen:', style=wx.ALIGN_CENTER), 0, wx.EXPAND),
            (self.etiqueta,1, wx.EXPAND),
            (wx.StaticText(self, label='Ruta de la imagen:', style=wx.ALIGN_CENTER), 0, wx.EXPAND),
            (self.ruta, 0, wx.EXPAND),
            (wx.StaticText(self, label='Etiqueta generada 1:', style=wx.ALIGN_CENTER), 0, wx.EXPAND),
            (self.generada1, 0, wx.EXPAND),
            (wx.StaticText(self, label='Error para etiqueta 1:', style=wx.ALIGN_CENTER), 0, wx.EXPAND),
            (self.error1, 0, wx.EXPAND),
            (wx.StaticText(self, label='Etiqueta generada 2:', style=wx.ALIGN_CENTER), 0, wx.EXPAND),
            (self.generada2, 0, wx.EXPAND),
            (wx.StaticText(self, label='Error para etiqueta 2:', style=wx.ALIGN_CENTER), 0, wx.EXPAND),
            (self.error2, 0, wx.EXPAND),
            (wx.StaticText(self, label='Etiqueta generada 3', style=wx.ALIGN_CENTER), 0, wx.EXPAND),
            (self.generada3, 0, wx.EXPAND),
            (wx.StaticText(self, label='Error para etiqueta 3:', style=wx.ALIGN_CENTER), 0, wx.EXPAND),
            (self.error3, 0, wx.EXPAND)])

        vbox.Add(gs, proportion=1, flag=wx.EXPAND)
        self.SetSizer(vbox)
