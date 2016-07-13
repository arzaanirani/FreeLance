import unittest

import twitterverse_functions as tf


class TestGetFilterResults(unittest.TestCase):
    '''Your unittests here'''

    def test_query_1(self):
        '''
        This tests the first query to test whether or not the search method completes as expected
        '''
        data_file = open('data.txt', 'r')
        data = tf.process_data(data_file)
        data_file.close()
        query_file = open('query1.txt', 'r')
        query = tf.process_query(query_file)
        query_file.close()
        search_results = tf.get_search_results(data, query['search'])
        filtered_results = tf.get_filter_results(data, search_results,
                                                 query['filter'])
        self.assertSequenceEqual(set(filtered_results), {'NicoleKidman', 'katieH'})

    def test_query_2(self):
        '''
        This tests the second query data file to test whether or not the
        search AND filter method completes as expected
        '''
        data_file = open('data.txt', 'r')
        data = tf.process_data(data_file)
        data_file.close()
        query_file = open('query2.txt', 'r')
        query = tf.process_query(query_file)
        query_file.close()
        search_results = tf.get_search_results(data, query['search'])
        filtered_results = tf.get_filter_results(data, search_results,
                                                 query['filter'])
        self.assertSequenceEqual(set(filtered_results), {'a', 'b'})

    def test_query_3(self):
        '''
        This tests the third query data file with the real twitterverse rdata.txt
        to test whether or not the search AND filter method completes as expected
        '''
        data_file = open('rdata.txt', 'r')
        data = tf.process_data(data_file)
        data_file.close()
        query_file = open('query4.txt', 'r')
        query = tf.process_query(query_file)
        query_file.close()
        search_results = tf.get_search_results(data, query['search'])
        filtered_results = tf.get_filter_results(data, search_results,
                                                 query['filter'])
        self.assertSequenceEqual(set(filtered_results), {'jddimarco', 'cssu', 'karenreid', 'yorchopolis', 'VevWong', 'flaps', 'irvingreid', 'pgries'})


if __name__ == '__main__':
    unittest.main(exit=False)
