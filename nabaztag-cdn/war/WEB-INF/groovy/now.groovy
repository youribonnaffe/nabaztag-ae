def now = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"))
redirect "/clock/"+now[Calendar.HOUR]+"/"+(Rand.random.nextInt(6)+1)+".mp3"