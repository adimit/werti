<?xml version="1.0" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
     <head>
	  <link rel="stylesheet" type="text/css" href="/werti/werti.css"/>
	  <link rel="shortcut icon" type="image/x-icon" href="favicon.ico"/>

	  <meta http-equiv="content-type" content="text/html; UTF-8"/>
	  <meta http-equiv="content-language" content="de"/>
	  <meta name="author" content="Александър Димитров"/>
	  <title>Welcome to WERTi</title>
     </head>
     <body>
	  <h1>Welcome to WERTi</h1>
	  <div class="text">
	       <div class="notice">
		    This is the developer's backend of WERTi. If you're a user, we're sorry, but the user frontend
		    is not yet written. Check back later.
	       </div>
	       Please spcify a URL you want to send through the system, an input enhancement method
	       and a set of tags you want to see enhanced in the website's tags. Please note that some pages
	       might still look different from what you expect. Please note problems to the
	       <a href="mailto:admtrov@sfs.uni-tuebingen.de">administrator</a>.
	  </div>
	  <h2>Settings</h2>
	  <form action="Interface" method="POST">
	       <div class="text">
		    <p>
			 URL or search string: <input type="text" name="termOrUrl" size="30"/><br/>
		    </p>
		    <fieldset>
			 <legend>A tokenizer:</legend>
			 <input type="radio" name="tokenizer" checked="checked" value="ptb"/>Stanford's Penn Tree Bank tokenizer (slow)<br/>
			 <input type="radio" name="tokenizer" value="lgp"/>LingPipe tokenizer (currently not working)<br/>
			 <input type="radio" name="tokenizer" value="own"/>Self-Made tokenizer (currently not working)<br/>
		    </fieldset>
		    <fieldset>
			 <legend>A part of speech tagger: </legend>
			 <input type="radio" name="tagger" checked="checked" value="ptb"/>Penn Tree Bank trained Stanford tagger (slow)<br/>
			 <input type="radio" name="tagger" value="hmm"/>Hidden Markov Model Tagger <br/>
			 <input type="radio" name="tagger" value="lgp"/>LingPipe tagger (currently not working)<br/>
			 <p>
			      A set of tags: <input value="NN, IN" type="text" name="tags" size="30"/><br/>
			 </p>
			 <div class="notice">
			      Please note that (for now) you'll have to carefully choose
			      the part of speech tags you want to see visually enhanced, since the interface
			      doesn't infer them automatically for you. So if you choose to use the Stanford tagger,
			      use the Penn Tree Bank tagging conventions.
			 </div>
		    </fieldset>
		    <fieldset>
			 <legend>A visual input enhancement method:</legend>
			 <input type="radio" name="enhance" checked="checked" value="clr"/>Color-enhence the words.<br/>
			 <input type="radio" name="enhance" value="ask"/>Click on the right words.<br/>
			 <input type="radio" name="enhance" value="fib"/>Fill in the blanks.<br/>
		    </fieldset>
		    <p>
			 <input type="submit" value="Enhance"/>
		    </p>
	       </div>
	  </form>
	  <h2>Further Information</h2>
	  <div class="text">
	       Should you have questions about the system, you may 
	       <a href="mailto:admtrov@sfs.uni-tuebingen.de">contact me</a>. You can also head over to 
	       <a href="docs/">the documentation</a>
	  </div>
     </body>
</html>

