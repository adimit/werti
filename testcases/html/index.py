from mod_python import psp, util
from mod_python import apache

def index(req):
	req.content_type= 'text/plain'
	req.write("Hello, World")
