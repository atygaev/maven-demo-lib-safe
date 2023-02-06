rm -rf $HOME/$1/repository/com/example/maven-demo-lib

artifactLocation=$HOME/$1/repository/com/example/maven-demo-lib

rm -rf $artifactLocation

mkdir -p $artifactLocation

mv $HOME/.m2/repository/com/example/maven-demo-lib ${artifactLocation}/../

find $artifactLocation | sort
