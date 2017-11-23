from enum import Enum

class TargetType(Enum):
	classification=1,
	regression = 2,
	multi_regression = 3,
	vector_generation = 4,
	image_generation = 5,
	string = 6
	map = 7,
