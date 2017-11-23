from random import randint
from PIL import Image, ImageDraw, ImageFont
import os
import numpy as np
from sys import platform
from extensions import *
from enum import Enum

# Custom imports
from .target import *
from .kind import *
from .targettype import *
from .batch import *

class letter():
	def __init__(self, *margs, **args):
		if not args:
			if margs: args=margs[0]
			else:args={}

		self.font = args['font'] if 'font' in args else pick(fontnames)
		self.size = args['size'] if 'size' in args else pick(sizes)
		self.char = args['char'] if 'char' in args else pick(letterz)
		self.back = args['back'] if 'back' in args else None
		self.ord	= args['ord'] if 'ord' in args else ord(self.char)
		self.pos	= args['pos'] if 'pos' in args else {'x':pick(range(0,max_padding)),'y':pick(range(0, max_padding))}
		self.angle= args['angle'] if 'angle' in args else 0
		self.color= args['color'] if 'color' in args else 'black'
		self.style= args['style'] if 'style' in args else self.get_style(self.font)
		self.invert=args['invert'] if 'invert' in args else pick([-1,0,1])

	def projection(self):
		return self.matrix(),self.ord

	def get_style(self,font):
		if 'BI' in font: return 'bold&italic'
		if 'IB' in font: return 'bold&italic'
		if 'BoldItalic' in font: return 'bold&italic'
		if 'Black' in font: return 'bold' #~
		if 'Bol' in font: return 'bold'
		if 'bold' in font: return 'bold'
		if 'Bd' in font: return 'bold'
		if 'B.' in font: return 'bold'
		if 'B-' in font: return 'bold'
		if '_RB' in font: return 'bold'
		# if '-B' in font: return 'bold'
		# if '_B' in font: return 'bold'
		if 'Ita' in font: return 'italic'
		if 'It.' in font: return 'italic'
		if 'I.' in font: return 'italic'
		if 'I-' in font: return 'italic'
		if '_RI' in font: return 'italic'
		if 'Demi' in font: return 'medium'
		if 'Medi' in font: return 'medium'
		if 'Light' in font: return 'light'
		if 'Lt.' in font: return 'light'
		if 'Thin' in font: return 'light'
	# Mono
		return 'regular'

	def matrix(self, normed=true):
		matrix = np.array(self.image())
		if normed: matrix=matrix/ 255.
		if self.invert == -1:
			matrix = 1 - 2 * matrix # -1..1
		elif self.invert:
			matrix = 1 - matrix # 0..1
		return matrix
		# except:
		# 	return np.array(max_size*(max_size+extra_y))

	def load_font(self):
		fontPath = self.font if '/' in self.font else fonts_dir + self.font
		try:
			fontPath = fontPath.strip()
			ttf_font = ImageFont.truetype(fontPath, self.size)
		except:
			raise Exception("BAD FONT: " + fontPath)
		return ttf_font

	def image(self):
		ttf_font = self.load_font()
		padding = self.pos
		text = self.char
		size = ttf_font.getsize(text)
		size = (size[0], size[1] + extra_y)	# add margin
		size = (self.size, self.size)	# ignore rendered font size!
		size = (max_size, max_size + extra_y)	# ignore font size!
		if self.back:
			img = Image.new('RGBA', size, self.back) # background_color
		else:
			img = Image.new('L', size, 'white')	# # grey
		draw = ImageDraw.Draw(img)
		# -
		draw.text((padding['x']-shift_left, padding['y']-shift_up), text, font=ttf_font, fill=self.color)
		if self.angle:
			rot = img.rotate(self.angle, expand=1).resize(size)
			if self.back:
				img = Image.new('RGBA', size, self.back)  # background_color
			else:
				img = Image.new('L', size,'#FFF')#FUCK BUG! 'white')#,'grey')  # # grey
			img.paste(rot, (0, 0), rot)
		return img

	def show(self):
		self.image().show()

	@classmethod
	def random(cls):
		l=letter()
		l.size=pick(sizes)
		l.font=pick(fontnames)
		l.char=pick(letterz)
		l.ord=ord(l.char)
		l.position=(pick(range(0,10)),pick(range(0,10)))
		l.offset=l.position
		l.style=pick(styles) #None #
		l.angle=0

	def __str__(self):
		format="letter{char='%s',size=%d,font='%s',angle=%d,ord=%d,pos=%s}"
		return format % (self.char, self.size, self.font, self.angle, ord(self.char), self.pos)

	def __repr__(self):
		return self.__str__()
	# def print(self):
	# 	print(self.__str__)
	def save(self, path):
		self.image().save(path)

overfit = True
if overfit:
	print("[Letter]: Usando sobreajuste")
	min_size = 24
	max_size = 24
	max_padding = 8
	max_angle = 0
else:
	print("[Letter]: No usando sobreajuste")
	min_size = 8
	max_size = 32
	max_padding=10
	max_angle=45

shift_up = 9
shift_left = 2
min_char = 32 # Evitar chars que no se usan de la tabla ASCII
offset = 32 # Evitar chars que no se usan de la tabla ASCII
extra_y = 0

sizes=range(min_size,max_size)
if min_size==max_size: sizes=[min_size]
letterz= list(map(chr, range(min_char, 128)))
nLetters=len(letterz)

def find_fonts():
	if platform == "darwin":
		os.system("mdfind -name '.ttf' | grep '.ttf$' |grep -v 'Noto\|NISC' | iconv -f utf-8 -t ascii  > fonts.list")
	else:
		os.system("locate '.ttf' | grep '.ttf$' |grep -v 'mstt' > fonts.list")

def check_fonts():
	for font in fontnames:
		try:
			if not '/' in font :
				ImageFont.truetype(fonts_dir+font, max_size)
				ImageFont.truetype(fonts_dir+font, min_size)
			else:
				ImageFont.truetype(font, max_size)
				ImageFont.truetype(font, min_size)
		except:
			fontnames.remove(font)

if not os.path.exists("fonts.list"):
	print("[Letter]: Generando fonts.list")
	find_fonts()
else:
	print("[Letter]: Usando fonts.list en cache")

fonts_dir="/data/fonts/"
fontnames=readlines("fonts.list")
check_fonts()

if overfit:
	fontnames=fontnames[0:1]

if overfit:
	fontnames=['Menlo.ttc']


styles=['regular','light','medium','bold','italic']

color_channels = 1  # gray

def random_color():
	if color_channels<=1:
		return None
	r = randint(0, 255)
	g = randint(0, 255)
	b = randint(0, 255)
	a = randint(0, 255)
	return (r, g, b, a)

nClasses={ # / dimensions
	Target.letter: nLetters,  # classification
	Target.font: len(fontnames),  # classification
	Target.style: len(styles),  # classification
	Target.size: 1,  # max_size # regression
	Target.angle: 1,  # max_angle # regression
	Target.position: 2,  # x,y # regression
	Target.color: 3, # RGB regression
}

targetTypes={
	Target.letter: TargetType.classification,
	Target.font: TargetType.classification, # or string
	Target.style: TargetType.classification,
	Target.size: TargetType.regression,
	Target.angle: TargetType.regression,
	Target.position: TargetType.multi_regression, # x,y or box
	Target.color: TargetType.multi_regression, # multi
	Target.text: TargetType.string,
	# Target.word: TargetType.string,
}

def pos_to_arr(pos):
	return [pos['x'],pos['y']]

def pick(xs):
	return xs[randint(0,len(xs)-1)]

def one_hot(item, num_classes,offset):
	labels_one_hot = numpy.zeros(num_classes)
	labels_one_hot[item-offset] = 1
	return labels_one_hot

if __name__ == "__main__":
	while 1:
		l = letter()
		mat=l.matrix()
		print(np.max(mat))
		print(np.min(mat))
		print(np.average(mat))
