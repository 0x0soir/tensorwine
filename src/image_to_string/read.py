import pytesser.pytesser as pytesser

text = pytesser.image_file_to_string('texto.jpg', graceful_errors=True)
print (text)
