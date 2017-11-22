#!/usr/bin/env python
import sys
import os
import subprocess
from fontTools.ttLib import TTFont
from FontMapper import *

mapper = FontMapper("fonts", "images", "texts", "22")

mapper.build_text_files()
mapper.build_img_files()
