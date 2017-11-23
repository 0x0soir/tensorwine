from .target import *

class batch():

	def __init__(self,target=Target.letter, batch_size=64):
		self.batch_size=batch_size
		self.target= target
		self.shape=[max_size * max_size+extra_y, nClasses[target]]
		self.train= self
		self.test = self
		self.test.images,self.test.labels = self.next_batch()

	def next_batch(self,batch_size=None):
		letters=[letter() for i in range(batch_size or self.batch_size)]
		def norm(letter):
			return letter.matrix()
		xs=map(norm, letters) # 1...-1 range
		if self.target==Target.letter: ys=[one_hot(l.ord,nLetters,offset) for l in letters]
		if self.target == Target.size: ys = [l.size for l in letters]
		if self.target == Target.position: ys = [pos_to_arr(l.pos) for l in letters]
		return list(xs), list(ys)
