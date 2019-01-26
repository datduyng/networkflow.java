## Using git.

1) Have a new features?
2) Create a new branch.
   - GUI on github.com
     - Type name of the new branch  says `BRANCH_NAME` <under 'Branch' button.
     > <img width="200" height="200" src="https://user-images.githubusercontent.com/35666615/51780247-a1209980-20d2-11e9-8a7e-76134c2a3d25.jpg">
     - go back to cli and type `git fetch`// update remote branch
     - `git checkout BRANH_NAME`
     
   - With command line
     - `git checkout -b BRANCH_NAME` // created a branch name BRANCH_NAM

3) Develop code make changes
- make sure you type `git checkout BRANCH_NAME` to switch to new branch that you just created
  - `git branch` to show all available branch
  
4) Upload files and update changed
   - Using GUI
     - Go to the github page under **New Branch that we just created**
     - click 'upload files' then drags and drop files that we just changed over

   - Using CLI(command line Interface)
     ```
     git checkout BRANCH_NAME #make sure we switched to new branch
     git status #Show what have been changed
     git add . # added all changed
     git commit -m "Some customize message here like -update-"
     git push 
     ```
     
5) create a pull request to the master.
- This can be done by

```

```

5) Voila.

6) some other importance cmd
- create new branch from sub branch other than master
```
git fetch # update remote branched
git branch #view all branch you probability don't see branch that you did not created 
git checkout OTHER_BRANCH  
#via cli
git checkout -b NEW_BRANCH OTHER_BRANCH
git checkout NEW_BRANCH # switch back to the branch we just created
```