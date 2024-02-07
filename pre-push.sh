#!/bin/sh
#
# This hook is for Android project git repos.
#
# You can use git config variables to customize this hook.
# -----------------------------------------------------------
# Change hooks.lintTargetDirectory to point at a non . directory
# Change hooks.lintArgs to add any custom lint arguments you prefer

# Get custom info
dirToLint=$(git config hooks.lintTargetDirectory)
lintArgs=$(git config hooks.lintArgs)
projectDir=$(git rev-parse --show-toplevel)
lintReportPath="/${projectDir}/lint-report.html"

# If user has not defined a preferred directory to lint against, make it .
if [ -z "$dirToLint"]
  then
  dirToLint="."
fi

# Perform lint check
echo "Performing pre-commit lint check of ""$dirToLint"
./gradlew spotlessApply
lintStatus=$?

if [ $lintStatus -ne 0 ]
then
  echo "Lint failure, git push aborted."
  echo "Open ${projectDir}${lintReportPath} in a browser to see Lint Report"
  exit 1
else
  echo "Lint successfully completed!"
fi

exit $lintStatus