- nonempty rows
- nonempty triangle
- actual triangle
- no inputs different from numbers and EOF 
- single minimal path

https://www.geeksforgeeks.org/minimum-sum-path-triangle/

Dynamical programming (bottom-up): 
```
d[i][j] = a[i][j] + min(d[i+1][j], d[i+1][j+1]), j=0..i, i=0..L-1
d[L][j] = 0, j=0..L
min sum is d[0][0]
```
plus backtracking.