
require(vcd) 
require(cluster)
require(psy)


# read data
data.all <- read.table("ske-results-R.csv", header=T, sep=",")

# build sums and select only those with a minimum number of occurrences
data.all.sums <- (data.all[,2] + data.all[,3] + data.all[,4])
data <- subset(data.all, data.all.sums > 25)




# normalize counts 
data.sums <- (data[,2] + data[,3] + data[,4])
data.norm <- data
data.norm[,2] <- data[,2] / data.sums 
data.norm[,3] <- data[,3] / data.sums 
data.norm[,4] <- data[,4] / data.sums 

#lemma <- data$lemma
attach(data)

threshold <- 25
pthres <- .90

# high percentage infinitives with a lot of ingforms
inf.undecided <- subset(data, data.norm$infinitive >= pthres & data$prep.ingform + data$ingform >= threshold)
inf.undecided$statcat <- "undecided"
# without a lot of ingforms
inf.certain   <- subset(data, data.norm$infinitive >= pthres & data$prep.ingform + data$ingform < threshold)
inf.certain$statcat <- "infinitive"

# high percentage ingforms with a lot if infinitives
ing.undecided <- subset(data, data.norm$prep.ingform + data.norm$ingform >= pthres & data$infinitive >= threshold)
ing.undecided$statcat <- "undecided"
ing.certain <- subset(data, data.norm$prep.ingform + data.norm$ingform >= pthres & data$infinitive <  threshold)
ing.certain$statcat <- "gerund"

both <- subset(data, data.norm$prep.ingform + data.norm$ingform <  pthres & data.norm$infinitive < pthres)
both$statcat <- "both"

# merge all classified subsets into a large data frame with classification
# column $statcat
data.classified <- merge(inf.undecided, inf.certain, all.x=T,all.y=T)
data.classified <- merge(data.classified, ing.certain, all.x=T,all.y=T)
data.classified <- merge(data.classified, ing.undecided, all.x=T,all.y=T)
data.classified <- merge(data.classified, both, all.x=T,all.y=T)

# compute Cohen's Kappa between grammar book rating and our classification
k.stat <- ckappa(data.frame(data.classified$grammarbook, data.classified$statcat))


# compute sinplified normalized values with ingform columns combined
#data.norm.simple <- data.frame(data.norm[,1], data.norm[,2], data.norm[,3] + data.norm[,4])
#data.simple <- data.frame(data[,1], data[,2], data[,3] + data[,4])
                          

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

postscript(file="ternary-plot1.ps") 
ternaryplot(
	data[,2:4],
	id_color="darkblue",
	cex = .8,
	dimnames = c("to+infinitive","gerund", "preposition+gerund"),
	main="",
	grid=T
 )
dev.off()

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