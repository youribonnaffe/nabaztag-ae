import com.google.appengine.api.datastore.Key

request.rabbits = datastore.execute {
	select all from rabbit where userId == user.userId
}

request.rfids = [:]
request.rabbits.each { rabbit ->
	request.rfids.put( rabbit.key.name , datastore.execute {
			select all from rfid where 'rabbit' == rabbit.key.name sort asc by name
			}
	)
}

forward '/WEB-INF/pages/home.gtpl'
