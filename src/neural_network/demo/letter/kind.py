from enum import Enum

class Kind(Enum):
	blank = 0
	letter = 1
	digit = 2
	background = 3
	line = 4
	emoji = 5
	colour_image = 6
	black_and_white_image = 7
	icon = 8
	latin = 9
	mixed = 10
	arabic = 11
	chinese = 12
	cyril = 13
	unicode = 14
