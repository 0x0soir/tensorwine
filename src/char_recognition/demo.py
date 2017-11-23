import wx
import mainFrame
import letter
import layer

if __name__ == '__main__':
    # Cargar red neuronal
    net = layer.net(model="denseConv", input_shape=[letter.max_size, letter.max_size])
    # Abrir ventana
    app = wx.App(redirect=False)
    frame = mainFrame.MainFrame(net)
    app.MainLoop()
