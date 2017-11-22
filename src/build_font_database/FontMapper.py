#!/usr/bin/env python
import sys
import os
import subprocess
import shutil
import glob
from fontTools.ttLib import TTFont

HEADER = '\033[95m'
OKBLUE = '\033[94m'
OKGREEN = '\033[92m'
WARNING = '\033[93m'
FAIL = '\033[91m'
ENDC = '\033[0m'
BOLD = "\033[1m"

class FontMapper:
    def __init__(self, fonts_dir, img_dir, txt_dir, char_size):
        self.__FONTS_DIR    = fonts_dir
        self.__IMAGES_DIR   = img_dir
        self.__TEXT_DIR     = txt_dir
        self.__CHAR_SIZE    = char_size

        print(
            OKBLUE +
            "[FontMapper] [init] Inicio correcto. Lectura de fuentes: %s | Escritura de img: %s | Escritura de texto: %s | Font size: %s"
            % (self.__FONTS_DIR, self.__IMAGES_DIR, self.__TEXT_DIR, self.__CHAR_SIZE)
            + ENDC
        )
        print(
            WARNING +
            "[FontMapper] [init] Escritura de ficheros de texto: %s"
            % (self.__TEXT_DIR)
            + ENDC
        )
        print(
            WARNING +
            "[FontMapper] [init] Escritura de iconos: %s"
            % (self.__IMAGES_DIR)
            + ENDC
        )
        print(
            WARNING +
            "[FontMapper] [init] Tamaño de iconos: %s"
            % (self.__IMAGES_DIR)
            + ENDC
        )

    def build_text_files(self):
        self.__clear_text_files()
        self.__check_fonts_dir()

        for d in [self.__TEXT_DIR, self.__IMAGES_DIR]:
            if not os.path.isdir(d):
                print(
                    OKBLUE +
                    "[FontMapper] [build_text_files] Crea directorio: %s"
                    % (d)
                    + ENDC
                )
                os.mkdir(d)

        for font in glob.glob(self.__FONTS_DIR+'/*.ttf'):
            ttf = TTFont(font, 0, allowVID=0, ignoreDecompileErrors=True, fontNumber=0)
            font_name = os.path.basename(os.path.splitext(font)[0])

            if not os.path.exists(self.__TEXT_DIR + '/' + font_name):
                os.mkdir(self.__TEXT_DIR + '/' + font_name)

            for x in ttf["cmap"].tables:
                for y in x.cmap.items():
                    char_utf8 = chr(y[0])
                    char_name = y[1]
                    f = open(os.path.join(self.__TEXT_DIR + '/' + font_name, '%s_%d.txt' % (y[1], y[0])), 'w')
                    f.write(char_utf8)
                    f.close()
            ttf.close()

            print(
                OKBLUE +
                "[FontMapper] [build_text_files] Nueva fuente reconocida: %s"
                % (font)
                + ENDC
            )

    def build_img_files(self):
        files = os.listdir(self.__TEXT_DIR)

        for file_dir in files:
            if not os.path.exists(self.__IMAGES_DIR + '/' + file_dir):
                os.mkdir(self.__IMAGES_DIR + '/' + file_dir)
            for file_txt in glob.glob(self.__TEXT_DIR+"/"+file_dir+'/*.txt'):
                input_txt   = file_txt
                output_png  = "PNG32:"+self.__IMAGES_DIR + "/"+file_dir+"/"+os.path.basename(os.path.splitext(input_txt)[0])+".png"
                subprocess.call(["convert", "-font", self.__FONTS_DIR+"/"+file_dir+".ttf", "-size", "24x24", "-fill", "blue", "-gravity", "center", "-channel", "rgba", "-alpha", "on", "-pointsize", self.__CHAR_SIZE, "-background", "white", "label:@" + input_txt, output_png])

            print(
                OKGREEN +
                "[FontMapper] [build_img_files] Iconos generados para la fuente: %s"
                % (file_dir)
                + ENDC
            )
    def __clear_text_files(self):
        shutil.rmtree(self.__TEXT_DIR, ignore_errors=True)
        shutil.rmtree(self.__IMAGES_DIR, ignore_errors=True)

        print(
            OKBLUE +
            "[FontMapper] [clear_text_files] Limpieza de imágenes y textos previos correcta"
            + ENDC
        )

    def __check_fonts_dir(self):
        if not os.path.exists(self.__FONTS_DIR):
            print( FAIL + "-> [FontMapper] [check_fonts_dir] No existe el directorio: " + self.__FONTS_DIR + ENDC)
            sys.exit()
