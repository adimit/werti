
require(vcd) 
require(cluster)


# read data
data <- read.table("ske-results-R.csv", header=T, sep=",")

# normalize counts 

data.sums <- (data[,2] + data[,3] + data[,4])
data.norm <- data
data.norm[,2] <- data[,2] / data.sums 
data.norm[,3] <- data[,3] / data.sums 
data.norm[,4] <- data[,4] / data.sums 

#lemma <- data$lemma
attach(data)

data.norm.simple <- data.frame(data.norm[,1], data.norm[,2], data.norm[,3] + data.norm[,4])
data.simple <- data.frame(data[,1], data[,2], data[,3] + data[,4])
                          

X11()
ternaryplot(
	data[,2:4],
	pch= 1,
	id_color="darkblue",
	id= lemma,
	cex = .1,
	dimnames = c("to+infinitive","gerund", "preposition+gerund"),
	main = "Verbs used with to-infinitive vs. gerund",
	grid=T
 )

X11()
ternaryplot(
	data[,2:4],
	id_color="darkblue",
	cex = .4,
	dimnames = c("to+infinitive","gerund", "preposition+gerund"),
	main = "Verbs used with to-infinitive vs. gerund",
	grid=T
 )

#X11()
#clustering1.diss <- daisy(data.norm[,2:4])
#clustering1.clus <- pam(clustering1.diss, 3, diss = TRUE)$clustering
#clusplot(clustering1.diss, clustering1.clus, diss = TRUE, shade = TRUE)

#X11()
#clustering1.diss <- daisy(data.norm.simple[,2:3])
#clustering1.clus <- pam(clustering1.diss, 2, diss = TRUE)$clustering
#clusplot(clustering1.diss, clustering1.clus, diss = TRUE, shade = TRUE, main="Verbs used with #to-infinitive vs. gerund")

# selecting subsets
# subset(data,data.norm$infinitive > 0.9 & data$ingform < 100)