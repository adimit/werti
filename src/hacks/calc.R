
require(vcd) 
require(cluster)
#require(A2R)


# read data
data <- read.table("ske-results-R.csv", header=T, sep=",")

# normalize counts 

data.sums <- (data[,2] + data[,3] + data[,4])
data.norm <- data
data.norm[,2] <- data[,2] / data.sums 
data.norm[,3] <- data[,3] / data.sums 
data.norm[,4] <- data[,4] / data.sums 


lemma <- data$lemma

 #colors <- c("black","red","green","blue","red","black","blue")

 #ternaryplot(
   #data.norm[,2:4],
   #pch = as.character(lemma),
   #id= lemma
   #col = colors[as.numeric(lemma)],
   #main = "Verbs"
 #)
# grid_legend(0.8, 0.9, pch, colors, levels(Positions),
#   title = "POSITION(S)")
                          

X11()
ternaryplot(
	data.norm[,2:4],
pch= 1,
id_color="darkblue",
	id= lemma,
	#prop_size=T,
	cex = .1,
#cex.lab=0.3,
	dimnames = c("to+infinitive","preposition+gerund", "gerund"),
grid=F

 )

X11()
clustering1.diss <- daisy(data.norm[,2:4])
clustering1.clus <- pam(clustering1.diss, 3, diss = TRUE)$clustering
clusplot(clustering1.diss, clustering1.clus, diss = TRUE, shade = TRUE)


#A2Rplot(h.usa, 
#        k=3, 
#        fact.sup=some.factor, 
#        criteria=hubertgamma,
#        boxes = FALSE,
#        col.up = "gray",
#        col.down = c("orange","royalblue","green3"))

#A2Rplot(
#   data.norm[,2:4], 
#   k=3
#)

