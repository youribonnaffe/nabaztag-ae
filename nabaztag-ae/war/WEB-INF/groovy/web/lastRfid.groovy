package web

response.status = 200
def rabbits = datastore.execute {
	select all from rabbit where userId == user.userId
}
if(rabbits && rabbits[0].lastRfid){
	sout.println rabbits[0].lastRfid
}