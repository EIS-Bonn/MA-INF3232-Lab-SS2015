For adding a new notification interval option - just copy any of existing files to the same folder with new filename.
Filenames should follow the next convention:
notify_%notification_interval%.php where %notification_interval is an interval name (we have 4 parameters for this field in DB now: off, hourly, daily, weekly. Name is a case sensitive!)

For recalculating the popularity of slides and decks start calculate_popularity.php script.