package de.gidonernst.jarversion

import java.io.File

import java.util.jar.JarFile
import java.util.jar.JarEntry

import scala.collection.JavaConversions._

object Jarversion {
  def minmax(r1: (Int, Int), r2: (Int, Int)) = {
    val (min1, max1) = r1
    val (min2, max2) = r2
    (Math.min(min1, min2), Math.max(max1, max2))
  }

  def jarversion(path: String) = {
    val jar = new JarFile(path)
    val classes = jar.entries.filter(_.getName.endsWith(".class"))

    def entryversion(entry: JarEntry) = {
      val in = jar.getInputStream(entry)

      assert(in.read == 0xCA)
      assert(in.read == 0xFE)
      assert(in.read == 0xBA)
      assert(in.read == 0xBE)

      val b4 = in.read
      val b5 = in.read
      val minor = (b4 << 8) | b5

      val b6 = in.read
      val b7 = in.read
      val major = (b6 << 8) | b7

      in.close
      
      (major, major)
    }
    
    if(classes.isEmpty) None
    else Some(classes.map(entryversion).reduce(minmax))
  }

  def main(args: Array[String]) = {
    for(path <- args) {
      jarversion(path) match {
        case None =>
        case Some((min, max)) =>
          println(path + " " + min + " " + max)
      }
    }
  }
}