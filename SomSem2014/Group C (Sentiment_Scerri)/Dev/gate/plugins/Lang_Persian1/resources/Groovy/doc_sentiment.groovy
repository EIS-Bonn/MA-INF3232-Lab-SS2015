//Count positive and negative sentences and predicate Sentiment of document
count = 0
score =0
def tfIdfDoc = [:]
pos=0
neg=0
Sentiment =0
sen_pos_score=0
sen_neg_score=0
doc.each{doc ->
  doc.getAnnotations()["Sentence"].each{anno ->
      count++
      score =score+ (int) anno.getFeatures()["score"]
      if (anno.getFeatures()["sentiment"]=="positive")
      {
             pos++
             sen_pos_score=sen_pos_score+(1/count)
       }
          else if (anno.getFeatures()["sentiment"]=="negative")
          {
              neg++
               sen_neg_score=sen_neg_score+(1/count)
              }
  }
  pos=(pos/count)*100
  neg=(neg/count)*100
  if ((pos-neg)<15 && (neg-pos)<15) S="neutral"
      else if(neg>pos && neg>15)S="negative"
          else if (pos>neg && pos>15)S="positive"
              else S="neutral"
}


println "Sentiment: "+S
println "Positive: "+ pos+"%"
println "Negative: "+ neg+"%"
println "Amount of Sentences: "+count
println "sen_neg_score: " +sen_neg_score
println "sen_pos_score: " +sen_pos_score
println "Score: "+score
println "----------------------------------------"
tfIdfDoc['sentiment']=S
tfIdfDoc['positive']=pos
tfIdfDoc['negative']=neg
doc.each{doc ->
    doc.features.Doc_sentiment= tfIdfDoc
 }