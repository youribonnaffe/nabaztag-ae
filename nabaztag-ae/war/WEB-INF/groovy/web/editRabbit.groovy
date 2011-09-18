package web;
import com.google.appengine.api.datastore.Entity

Entity rabbit = datastore.get('rabbit', params.rabbitKey);

request.rabbit = rabbit

forward("/WEB-INF/pages/rabbit-dialog.gtpl")