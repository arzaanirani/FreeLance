"""
Type descriptions of Twitterverse and Query dictionaries
(for use in docstrings)

Twitterverse dictionary:  dict of {str: dict of {str: object}}
    - each key is a username (a str)
    - each value is a dict of {str: object} with items as follows:
        - key "name", value represents a user's name (a str)
        - key "location", value represents a user's location (a str)
        - key "web", value represents a user's website (a str)
        - key "bio", value represents a user's bio (a str)
        - key "following", value represents all the usernames of users this 
          user is following (a list of str)
       
Query dictionary: dict of {str: dict of {str: object}}
   - key "search", value represents a search specification dictionary
   - key "filter", value represents a filter specification dictionary
   - key "present", value represents a presentation specification dictionary

Search specification dictionary: dict of {str: object}
   - key "username", value represents the username to begin search at (a str)
   - key "operations", value represents the operations to perform (a list of str)

Filter specification dictionary: dict of {str: str}
   - key "following" might exist, value represents a username (a str)
   - key "follower" might exist, value represents a username (a str)
   - key "name-includes" might exist, value represents a str to match (a case-insensitive match)
   - key "location-includes" might exist, value represents a str to match (a case-insensitive match)

Presentation specification dictionary: dict of {str: str}
   - key "sort-by", value represents how to sort results (a str)
   - key "format", value represents how to format results (a str)
       
"""

# Write your Twitterverse functions here


# --- Sorting Helper Functions ---
import collections


def tweet_sort(twitter_data, results, cmp):
    """ (Twitterverse list of Person, list of str, function) -> NoneType
    
    Sort the results list using the comparison function cmp and the data in 
    twitter_data.
    
    >>> twitter_data = {\
    'a':{'name':'Zed', 'location':'', 'web':'', 'bio':'', 'following':[]}, \
    'b':{'name':'Lee', 'location':'', 'web':'', 'bio':'', 'following':[]}, \
    'c':{'name':'anna', 'location':'', 'web':'', 'bio':'', 'following':[]}}
    >>> result_list = ['c', 'a', 'b']
    >>> tweet_sort(twitter_data, result_list, username_first)
    >>> result_list
    ['a', 'b', 'c']
    >>> tweet_sort(twitter_data, result_list, name_first)
    >>> result_list
    ['b', 'a', 'c']
    """

    # Insertion sort
    for i in range(1, len(results)):
        current = results[i]
        position = i
        while position > 0 and cmp(twitter_data, results[position - 1], current) > 0:
            results[position] = results[position - 1]
            position = position - 1
        results[position] = current


def more_popular(twitter_data, a, b):
    """ (Twitterverse list of Person, str, str) -> int
    
    Return -1 if user a has more followers than user b, 1 if fewer followers, 
    and the result of sorting by username if they have the same, based on the 
    data in twitter_data.
    
    >>> twitter_data = {\
    'a':{'name':'', 'location':'', 'web':'', 'bio':'', 'following':['b']}, \
    'b':{'name':'', 'location':'', 'web':'', 'bio':'', 'following':[]}, \
    'c':{'name':'', 'location':'', 'web':'', 'bio':'', 'following':[]}}
    >>> more_popular(twitter_data, 'a', 'b')
    1
    >>> more_popular(twitter_data, 'a', 'c')
    -1
    """

    followers = twitter_data['followers']

    a_popularity = len(followers[a])
    b_popularity = len(followers[b])
    if a_popularity > b_popularity:
        return -1
    if a_popularity < b_popularity:
        return 1
    return username_first(twitter_data, a, b)


def username_first(twitter_data, a, b):
    """ (Twitterverse list of Person, str, str) -> int
    
    Return 1 if user a has a username that comes after user b's username 
    alphabetically, -1 if user a's username comes before user b's username, 
    and 0 if a tie, based on the data in twitter_data.
    
    >>> twitter_data = {\
    'a':{'name':'', 'location':'', 'web':'', 'bio':'', 'following':['b']}, \
    'b':{'name':'', 'location':'', 'web':'', 'bio':'', 'following':[]}, \
    'c':{'name':'', 'location':'', 'web':'', 'bio':'', 'following':[]}}
    >>> username_first(twitter_data, 'c', 'b')
    1
    >>> username_first(twitter_data, 'a', 'b')
    -1
    """

    if a.lower() < b.lower():
        return -1
    if a.lower() > b.lower():
        return 1
    return 0


def name_first(twitter_data, a, b):
    """ (Twitterverse list of Person, str, str) -> int
        
    Return 1 if user a's name comes after user b's name alphabetically, 
    -1 if user a's name comes before user b's name, and the ordering of their
    usernames if there is a tie, based on the data in twitter_data.
    
    >>> twitter_data = {\
    'a':{'name':'Zed', 'location':'', 'web':'', 'bio':'', 'following':[]}, \
    'b':{'name':'Lee', 'location':'', 'web':'', 'bio':'', 'following':[]}, \
    'c':{'name':'anna', 'location':'', 'web':'', 'bio':'', 'following':[]}}
    >>> name_first(twitter_data, 'c', 'b')
    1
    >>> name_first(twitter_data, 'b', 'a')
    -1
    """

    a_name = twitter_data[a].name
    b_name = twitter_data[b].name
    if a_name < b_name:
        return -1
    if a_name > b_name:
        return 1
    return username_first(twitter_data, a, b)


def process_data(data_file):
    data = data_file.read().splitlines()
    following = collections.defaultdict(lambda: [])
    followers = collections.defaultdict(lambda: [])
    people = {}
    while len(data) > 1:
        username = data.pop(0)
        full_name = data.pop(0)
        location = data.pop(0)
        website = data.pop(0)
        bio = ''
        while data[0] != 'ENDBIO':
            bio += data.pop(0) + '\n'
        if data[0] == 'ENDBIO':
            data.pop(0)
        if username not in following:
            following[username] = []
        while data[0] != 'END':
            person = data.pop(0)
            following[username].append(person)
            if person not in followers:
                followers[person] = []
            followers[person].append(username)
        data.pop(0)
        people[username] = Person(username, full_name, location, website, bio)
    return {'people': people, 'following': following, 'followers': followers}


def process_query(query_file):
    query = query_file.read().splitlines()[1:]
    search, filter, present = [], [], []
    while query[0] != 'FILTER':
        search.append(query.pop(0))
    query.pop(0)
    while query[0] != 'PRESENT':
        filter.append(query.pop(0))
    query.pop(0)
    while len(query) > 0:
        present.append(query.pop(0))
    return {'search': search, 'filter': filter, 'present': present}


class Person(object):
    def __init__(self, user, full_name, location, website, bio):
        self.user = user
        self.full_name = full_name
        self.location = location
        self.website = website
        self.bio = bio


if __name__ == '__main__':
    import doctest

    doctest.testmod()


def get_search_results(data, search):
    people = data['people']
    following = data['following']
    followers = data['followers']
    username = search.pop(0)
    out = set()
    out.add(username)
    while len(search) > 0:
        value = search.pop(0)
        if value == 'following':
            temp = set()
            for i in out:
                temp |= set([j for j in following[i]])
            out = temp
        else:
            temp = set()
            for i in out:
                temp |= set([j for j in followers[i]])
            out = temp
    return list(out)


def get_filter_results(data, search_results, filter):
    people = data['people']
    following = data['following']
    followers = data['followers']
    search_results = set(search_results)
    while len(filter) > 0:
        current = filter.pop(0)
        if current.startswith('name-includes'):
            current = current[14:]
            temp = set()
            for i in search_results:
                if current.lower() in people[i].full_name.lower():
                    temp.add(i)
            search_results &= temp
        elif current.startswith('location-includes'):
            current = current[18:]
            temp = set()
            for i in search_results:
                if current.lower() in people[i].location.lower():
                    temp.add(i)
            search_results &= temp
        elif current.startswith('follower'):
            current = current[9:]
            # follower: keep all users who are following "current".
            temp = set()
            for i in search_results:
                if i in following[current]:
                    temp.add(i)
            search_results &= temp
        else:
            current = current[10:]
            # following: keep all users "current" follow.
            temp = set()
            for i in search_results:
                if i in followers[current]:
                    temp.add(i)
            search_results &= temp
    return list(search_results)


def get_present_string(data, filtered_results, present):
    people = data['people']
    following = data['following']
    followers = data['followers']
    sortby = present[0][8:]
    formatby = present[1][7:]
    if sortby == 'username':
        tweet_sort(data, filtered_results, username_first)
    elif sortby == 'name':
        tweet_sort(data, filtered_results, name_first)
    else:
        tweet_sort(data, filtered_results, more_popular)
    out = ''
    if formatby == 'long':
        for person in filtered_results:
            out += '----------\n'
            a = people[person]
            out += a.user + '\n'
            out += 'name: ' + a.full_name + '\n'
            out += 'location: ' + a.location + '\n'
            out += 'website: ' + a.website + '\n'
            out += 'bio:\n' + a.bio
            out += 'following: ' + str(following[person]) + '\n'
        out += '----------'
    else:
        out += str(filtered_results)
    return out

def all_followers(data, username):
    return data['followers'][username]

