#!/usr/bin/env python
import sys
import os
import subprocess
from fontTools.ttLib import TTFont
from FontMapper import *

mapper = FontMapper("fonts", "images", "texts", "100")

mapper.build_text_files()
mapper.build_img_files()
