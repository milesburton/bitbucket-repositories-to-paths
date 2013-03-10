#!/bin/bash +x
SELF=$(basename $0)
SELF_PWD=`pwd`

REPOS=($(cat *.urls))

mkdir -p repos

for REPO in ${REPOS[*]}
	do

		cd $SELF_PWD/repos

    		echo "   Preparing repo: $REPO"

		if [ ! -d "$REPO" ]; then
			echo "Repo not detected.. Cloning"

			git clone --mirror $REPO			

			echo "Clone complete for : $REPO"
		fi

	done
