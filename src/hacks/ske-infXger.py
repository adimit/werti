#!/usr/bin/python

# a test for demonstration using Sketch Engine through json interface

import urllib, urllib2, base64
import simplejson
import sys

### initialize command line stuff
if len(sys.argv) < 5:
	sys.stderr.write("Missing arguments?! Arguments are, in that order:\n\n");
	sys.stderr.write("   SkE_User SkE_Password InputFile OutputFile\n\n");
	sys.exit(1)


usr = sys.argv[1];
passwd = sys.argv[2];
infile = sys.argv[3];
outfile = sys.argv[4];

base_url = 'http://beta.sketchengine.co.uk/auth/corpora/run.cgi/'


### sending a CQL query to sketch engine's concordancer
def cqlQuery(query):
	
	sys.stderr.write("QUERY: %s\n" % (query))
	
	# prepare parameters
	method = 'view'

	attrs = dict(corpname='preloaded/bnc', q='q'+query,
               format='json')
	encoded_attrs = urllib.quote(simplejson.JSONEncoder().encode(attrs))
	url = base_url + method + '?json=%s' % encoded_attrs

	# creating query string
	request = urllib2.Request(url)

	# authentication
	base64string = base64.encodestring('%s:%s' % (usr, passwd))[:-1]
	request.add_header("Authorization", "Basic %s" % base64string)

	# json data receiving
	file = urllib2.urlopen(request)
	data = file.read()
	file.close()
	
	return data;

### initializing I/O before taking other actions
IN = open(infile, "r");
OUT = open(outfile, "w");

# output header line
OUT.write("lemma,infinitive,ingform,prep+ingform,prep+infinitive\n")
	
### loop over input
for line in IN:
	
	lemma = line.strip().lower()
	if ( lemma == "") :
		continue
	
	if lemma.startswith("#"):
		OUT.write("%s\n" % (lemma))
		continue
	
	OUT.write("%s," % (lemma))
	
	data = cqlQuery('[lemma="%s" & tag="V.*"] [tag="TO.*"] [tag="V.*I"]' %(lemma))
	try:
		json_obj = simplejson.loads(data)
		count = json_obj['concsize']
		OUT.write("%i," % (count))
	except:
		OUT.write("NA,")
		

	data = cqlQuery('[lemma="%s" & tag="V.*"] [tag="V.*G"]' %(lemma))
	try:
		json_obj = simplejson.loads(data)
		count = json_obj['concsize']
		OUT.write("%i," % (count))
	except:
		OUT.write("NA,")
	
	data = cqlQuery('[lemma="%s" & tag="V.*"] [tag="PRP"] [tag="V.*G"]' %(lemma))
	try:
		json_obj = simplejson.loads(data)
		count = json_obj['concsize']
		OUT.write("%i," % (count))
	except:
		OUT.write("NA,")
		
	data = cqlQuery('[lemma="%s" & tag="V.*"] [tag="PRP"]  [tag="TO.*"] [tag="V.*I"]' %(lemma))
	try:
		json_obj = simplejson.loads(data)
		count = json_obj['concsize']
		OUT.write("%i\n" % (count))
	except:
		OUT.write("NA\n")
		
	
	
	#OUT.write("%s,%i,%i,%i\n" % (lemma,infCount,gerCount, prepGerCount))

IN.close()
OUT.close()