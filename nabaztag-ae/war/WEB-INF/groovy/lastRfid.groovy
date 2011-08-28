
response.status = 200
def rabbits = datastore.execute {
	select all from rabbit where userId == user.userId
}
sout.println rabbits[0].lastRfid