#Analyse des fitness


plotLine <- function(x,y,data,xlim,ylim){
  vx = data$x
  vy = data$y
  stdy = 

  plot(func,data=data,type='l',col='red',xlim=xrange,ylim=yrange)
  sd = 1
  segments(x,y-sd,x,y+sd)
  epsilon <- 0.02
  segments(x-epsilon,y-sd,x+epsilon,y-sd)
  segments(x-epsilon,y+sd,x+epsilon,y+sd)
}



#Compute stat of data fame
doSummary <- function(x,y,d){
  alpha = 0.05
  # This data frame calculates statistics for each x.
  d.summary <- data.frame(
      x=unique(d[[x]]),
      mean=tapply(d[[y]], d[[x]], mean),
      n=tapply(d[[y]], d[[x]], length),
      sd=tapply(d[[y]], d[[x]], sd)
      )
  # Precalculate standard error of the mean (SEM)
  d.summary$sem <- d.summary$sd/sqrt(d.summary$n)
  
  # Precalculate margin of error for confidence interval
  d.summary$me <- qt(1-alpha/2, df=d.summary$n)*d.summary$sem
  return(d.summary)
}




d <- read.table("csv_stats.csv", header=TRUE, sep=",")



summary(d)
tmp <- subset(d,d[["nbRepetition"]]==10)
summary(tmp)
boxplot(tmp[["fitness"]])
test <- subset(tmp,tmp[["simuTime"]]==1)
summary(test)
dstat <-doSummary("proba","fitness",test)
summary(dstat)
plot(fitness~proba,data=subset(tmp,tmp[["simuTime"]]==1))
plot(mean~x,data=dstat)
require(ggplot2)
install.packages (pkgs=c ("labeling"), lib="/users/cortex/bchappet/.rkward/library")

ggplot(dstat, aes(x = x, y = mean)) +  
  geom_bar(position = position_dodge(), stat="identity", fill="blue") + 
  geom_errorbar(aes(ymin=mean-me, ymax=mean+me)) +
  ggtitle("Bar plot with 95% confidence intervals") + # plot title
  theme_bw() + # remove grey background (because Tufte said so)
  theme(panel.grid.major = element_blank()) # remove x and y major grid lines (because Tufte said so)

hist(fitness~proba*simuTime,tmp)

xrange <- range(tmp[["fitness"]]) 
yrange <- range(tmp[["proba"]]) 

plotLine(fitness~proba,subset(tmp,tmp[["simuTime"]]==1),xrange,yrange)
par(new = TRUE)
plot(fitness~proba,data=subset(tmp,tmp[["simuTime"]]==3),type='l',col='green',xlim=xrange,ylim=yrange)
par(new = TRUE)
plot(fitness~proba,data=subset(tmp,tmp[["simuTime"]]==5),type='l',col='black',xlim=xrange,ylim=yrange)
par(new = TRUE)




library(vioplot)

x1 <- tmp$fitness[tmp$simuTime == 1]
x2 <- tmp$fitness[tmp$simuTime == 3]
x3 <- tmp$fitness[tmp$simuTime == 5]
vioplot(x1, x2, x3, names=c("4 cyl", "6 cyl", "8 cyl"), col="gold")
title("Violin Plots fitness per simuTime")

