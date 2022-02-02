package com.project.glisemikindeks.service

import android.content.Context
import com.project.glisemikindeks.repositories.Repo
import com.project.glisemikindeks.db.DBHelper
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class Service(context: Context) {
    val url =
        "http://kolaydoktor.com/saglik-icin-yasam/diyet-ve-beslenme/besinlerin-glisemik-indeks-tablosu/0503/1"
    private lateinit var data: Elements
    private lateinit var doc: Document
    private lateinit var element :Elements

    val db = DBHelper(context)
    fun addCategory(){
        val catthread = Thread {
            doc = Jsoup.connect(url).ignoreContentType(true).timeout(15000).get()
            element = doc.allElements
            for (i in 1..13) {
                val cat =
                    element.select("#ctl00_icerik_UpdatePanel1 > table:nth-child($i) > tbody > tr:nth-child(1) > td > p > font > font > b")
                if (i % 2 == 1 && i != 1) {
                    db.addCat(cat.text())
                  //  println(cat.text())
                }
            }
        }
        val data1Thread = Thread {
            for (i in 3..49) {
                data = element.select("#ctl00_icerik_UpdatePanel1 > table:nth-child(3) > tbody > tr:nth-child($i) > td >  p > font > font > b")
                val name = data[0].text()
                val glisemik = data[1].text()
                val karbon =data[2].text()
                val cal =data[3].text()
                db.addFood(name, glisemik, karbon, cal, "1")
                 //  println(name.toString()+" "+ glisemik.toString() + karbon.toString() + cal.toString())
                //   println("1")

            }
        }
        val data2Thred = Thread {
            for (i in 3..13) {
                data = element.select("#ctl00_icerik_UpdatePanel1 > table:nth-child(5) > tbody > tr:nth-child($i) > td > p")
                if (data.size > 0){
                    val name =data[0].text()
                    val glisemik =data[1].text()
                    val karbon =if (data[2].text().toString().isNotEmpty()) data[2].text() else "0"
                    val cal =if (data[3].text().toString().isNotEmpty()) data[3].text() else "0"
                   // println(name.toString()+glisemik.toString()+karbon.toString()+cal.toString())
                    db.addFood(name, glisemik, karbon, cal, "2")

                }

                // println("2")
            }
        }
        val data3Thred = Thread {
            for (i in 3..27) {
                data =element.select("#ctl00_icerik_UpdatePanel1 > table:nth-child(7) > tbody > tr:nth-child($i)> td > p > font > font > b")
                val name =data[0].text()
                val glisemik = data[1].text()
                val karbon =data[2].text()
                val cal = data[3].text()
                //println(name.toString()+glisemik.toString()+karbon.toString()+cal.toString())

                db.addFood(name, glisemik, karbon, cal, "3")
                //  println("3")
            }
        }
        val data4Thread = Thread {
            for (i in 3..14) {
                data =element.select("#ctl00_icerik_UpdatePanel1 > table:nth-child(9) > tbody > tr:nth-child($i) > td > p > font > font > b")
                val name =data[0].text()
                val glisemik = data[1].text()
                val karbon =data[2].text()
                val cal =data[3].text()
                db.addFood(name, glisemik, karbon, cal, "4")
                 //println(name.toString()+glisemik.toString()+karbon.toString()+cal.toString())
                // println("4")
            }
        }
        val data5Thread = Thread {
            for (i in 3..6) {
                data =element.select("#ctl00_icerik_UpdatePanel1 > table:nth-child(11) > tbody > tr:nth-child($i) > td > p > font > font > b")
                val name =data[0].text()
                val glisemik = data[1].text()
                val karbon =data[2].text()
                val cal =data[3].text()
                db.addFood(name, glisemik, karbon, cal, "5")
             //    println(name+glisemik+karbon+cal)
                // println("5")
            }
        }

        val data6Thread = Thread {
            for (i in 3..9) {
                data =element.select("#ctl00_icerik_UpdatePanel1 > table:nth-child(13) > tbody > tr:nth-child($i) > td > p > font > font > b")
                val name =data[0].text()
                val glisemik = data[1].text()
                val karbon =data[2].text()
                val cal =data[3].text()
                db.addFood(name, glisemik, karbon, cal ,"6")
                // println(name.toString()+glisemik.toString()+karbon.toString()+cal.toString())
                //  println("6")
              //  Log.d("Son","splash")
            }
            Repo.son.postValue(true)
        }

        val run = Runnable {
            catthread.start()
            catthread.join()
            data1Thread.start()
            data1Thread.join()
            data2Thred.start()
            data2Thred.join()
            data3Thred.start()
            data3Thred.join()
            data4Thread.start()
            data4Thread.join()
            data5Thread.start()
            data5Thread.join()
            data6Thread.start()
            data6Thread.join()
        }
        Thread(run).start()


    }
}