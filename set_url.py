from subprocess import call
s = input('1.bitbucket\n2.git\n')
cmd = "git remote set-url origin"
rep = ""
if int(s) == 1:
    rep = "https://tohasvs@bitbucket.org/tohasvs/geo.git"
elif int(s) == 2:
    rep = "https://github.com/tohasvs/GeoPrj.git"
print(call(" ".join([cmd, rep]), shell=True))
