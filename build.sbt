name := "JavaESClient"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.typesafe"          	 %      "config"                %   "1.2.1",
  "org.elasticsearch"         %    "elasticsearch"         % "2.3.2",
  "org.scalatest"              %%     "scalatest"    	        %   "2.2.2"      %     "test"
)

parallelExecution in Test := false
