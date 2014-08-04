##Analyse des nb spike recus

##D'abord ./extractData.py
setwd("~/Dropbox/workspace_these/DNFSim2/statistiques/data/a_send/size9/time5/weigth0.7/dataBenoit")
d <- read.table("data.csv", header=TRUE, sep=",")
summary(d)

index1 <- subset(d,d[["index"]]==0)
summary(index1)

boxplot(index1[["nbSpike"]])

library(vioplot)


x0 <- subset(d,d[["index"]]==0)[["nbSpike"]]
x1 <- subset(d,d[["index"]]==10)[["nbSpike"]]
x2 <- subset(d,d[["index"]]==20)[["nbSpike"]]
x3 <- subset(d,d[["index"]]==30)[["nbSpike"]]
x4 <- subset(d,d[["index"]]==40)[["nbSpike"]]
x5 <- subset(d,d[["index"]]==50)[["nbSpike"]]
png('violin.png')
vioplot(x0,x1,x2,x3,x4,x5, names=c("index 0","index 10","index 20","index 30","index 40","index 50"), col="gold")
title("nbSpike per index")
dev.off()

var(index1[["nbSpike"]])
sd(index1[["nbSpike"]])