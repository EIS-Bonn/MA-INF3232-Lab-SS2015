//Count positive and negative sentences and predicate Sentiment of document
count = 0
score =0
def tfIdfDoc = [:]
pos=0
neg=0
Sentiment =0
doc.each{doc ->
  doc.getAnnotations()["Sentence"].each{anno ->
      count++
      score =score+ (int) anno.getFeatures()["score"]
      if (anno.getFeatures()["sentiment"]=="positive")
             pos++
          else if (anno.getFeatures()["sentiment"]=="negative")
              neg++
  }
  if(neg>pos)S="negative"
      else if (pos>neg)S="positive"
          else S="neutral"
}
pos=(pos/count)*100
neg=(neg/count)*100

println "Sentiment: "+S
println "Positive: "+ pos+"%"
println "Negative: "+ neg+"%"
println "Amount of Sentences: "+count
println "Score: "+score
println "----------------------------------------"
tfIdfDoc[1]=Sentiment
tfIdfDoc[2]=pos
doc.each{doc ->
    doc.features.Doc_sentiment= tfIdfDoc
 }