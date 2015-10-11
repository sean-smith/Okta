/* 	Write and save your SQL code in this file.
	Do not rename or move it.  */

select movie.title, actor.name from movie, movie_actor, actor where movie.id = movie_actor.movie_id and actor.id = movie_actor.actor_id;