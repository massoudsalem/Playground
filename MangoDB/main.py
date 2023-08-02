from bson import ObjectId
from pymongo import MongoClient

connstr = "mongodb+srv://xxx:x@cluster0.x.mongodb.net/?retryWrites=true&w=majority"
try:
    client = MongoClient(connstr)
    print("connection successful.\n")
except Exception:
    print(f"error {Exception}")

print(client.list_database_names())

# create the database
myDB = client['test']
# crate collection
ordersCollection = myDB['orders_collection']
# inserting a document
firstProduct = {
    '_id': 'id1',
    'product': 'toothbrush',
    'total_price': 15.25,
    'customer_name': 'Mohammad'
}
ordersCollection.insert_one(firstProduct)
print(client.list_database_names())
"""the output:
        connection successful.

        ['admin', 'local']
        ['test', 'admin', 'local']
"""

# insertMany
orders = [
    {
        "product": "CRS54EIC6JV",
        "total_price": 907.42,
        "customer_name": "Kane Vazquez"
    },
    {
        "product": "AUS45TRN4DP",
        "total_price": 777.08,
        "customer_name": "Amela Dodson"
    },
    {
        "product": "GKR68KWR2XN",
        "total_price": 1267.17,
        "customer_name": "Laith Mccormick"
    },
    {
        "product": "HON24VTK1FL",
        "total_price": 919.92,
        "customer_name": "September Mcfarland"
    },
    {
        "product": "XNJ84WWT8KU",
        "total_price": 1223.56,
        "customer_name": "Noble Heath"
    },
    {
        "product": "VGC39WWC1HI",
        "total_price": 822.08,
        "customer_name": "Samuel Martinez"
    },
    {
        "product": "FNS43JYI4HU",
        "total_price": 860.63,
        "customer_name": "Salvador Kim"
    },
    {
        "product": "FDX36VNB8FX",
        "total_price": 570.77,
        "customer_name": "Kieran O'donnell"
    },
    {
        "product": "RPB29ABJ7SY",
        "total_price": 585.99,
        "customer_name": "Hashim Holman"
    },
    {
        "product": "GVI28YPF6HI",
        "total_price": 1322.69,
        "customer_name": "Nola Patrick"
    },
    {
        "product": "HMW92NAM2MF",
        "total_price": 901.25,
        "customer_name": "Aimee Kane"
    },
    {
        "product": "YSY47RMD8YF",
        "total_price": 1030.82,
        "customer_name": "Nathan Riley"
    },
    {
        "product": "IOI46TIB4MP",
        "total_price": 1362.72,
        "customer_name": "Hyatt Guzman"
    },
    {
        "product": "SSC32RWX8XJ",
        "total_price": 783.48,
        "customer_name": "Eugenia Morse"
    },
    {
        "product": "VHC28SSZ1UY",
        "total_price": 1257.60,
        "customer_name": "Barry Kelly"
    },
    {
        "product": "QCI34EFX9DW",
        "total_price": 753.30,
        "customer_name": "Rhea Hays"
    },
    {
        "product": "YJC22GSO3TI",
        "total_price": 847.23,
        "customer_name": "Naomi Barry"
    },
    {
        "product": "FXD42KSK2SS",
        "total_price": 922.17,
        "customer_name": "Duncan Murray"
    },
    {
        "product": "QYV32NHX9AG",
        "total_price": 840.24,
        "customer_name": "Martina Nash"
    },
    {
        "product": "HUT46WLC3HZ",
        "total_price": 913.57,
        "customer_name": "Anjolie Orr"
    },
    {
        "product": "ODN78VYP8SG",
        "total_price": 651.58,
        "customer_name": "Juliet Hernandez"
    },
    {
        "product": "TXB12CRX3ZH",
        "total_price": 1078.23,
        "customer_name": "Rajah Rowe"
    },
    {
        "product": "KKP12SJL6AL",
        "total_price": 1005.46,
        "customer_name": "Graham Espinoza"
    },
    {
        "product": "EPJ23QJL1XL",
        "total_price": 986.64,
        "customer_name": "Lars Craig"
    },
    {
        "product": "JXT49GEW1FS",
        "total_price": 1007.42,
        "customer_name": "Igor Peck"
    },
    {
        "product": "QNK02EBH2VM",
        "total_price": 988.42,
        "customer_name": "Abel Ortiz"
    },
    {
        "product": "GRQ74XNL2SJ",
        "total_price": 801.78,
        "customer_name": "Patricia Lowe"
    },
    {
        "product": "SGL73RSP0MQ",
        "total_price": 1602.33,
        "customer_name": "Dale Clay"
    },
    {
        "product": "VHV58WBT6WC",
        "total_price": 684.92,
        "customer_name": "Stacy Nicholson"
    },
    {
        "product": "MSX13QTK5ZK",
        "total_price": 1007.95,
        "customer_name": "Alisa Soto"
    },
    {
        "product": "toothbrush",
        "total_price": 1007.95,
        "customer_name": "Alisa Soto"
    }
]
documents = ordersCollection.insert_many(orders)
print(documents)
"""
output:
        connection successful.

        ['test', 'admin', 'local']
        ['test', 'admin', 'local']
        <pymongo.results.InsertManyResult object at 0x000001BF840FF1C0>
"""
# reading data
# ##reading first document in the collection
query = ordersCollection.find_one()
print(query)
# ##reading all the collection (without any filter)
query = ordersCollection.find({})  # this is a cursor object (iterator)
print(query)
queryResult = [order for order in query]
print(queryResult)
# read with limit
query = ordersCollection.find({}).limit(5)
queryResult = [order for order in query]
print(queryResult)
# read using filters
query = ordersCollection.find({'product': "toothbrush"})
queryResult = [order for order in query]
print(queryResult)
# count orders for 'toothbrush'
query = ordersCollection.count_documents({'product': "toothbrush"})
print(query)
# count docs with price gt 1k
query = ordersCollection.count_documents({'total_price': {'$gt': 1000}})
print(query)
# count docs in range
query = ordersCollection.count_documents({'total_price': {"$gt": 500, "$lt": 1000}})
print(f'the number of products: {query}')
# read orders of products that contains the letter 'G' using regex
query = ordersCollection.find({'product': {'$regex': 'G{1}'}}, {"_id": 0})
queryResult = [order for order in query]
print(queryResult)
# sort orders by price higher
query = ordersCollection.find({}, {"_id": 0, "product": 1, "total_price": 1}).sort("total_price", -1)
queryResult = [order for order in query]
print(queryResult)
# read distinct products
query = ordersCollection.distinct('product')
print(query)
# sum the price of a product
query = ordersCollection.aggregate([{'$match': {'product': 'toothbrush'}},
                                    {"$group": {"_id": "$product", "total_price": {"$sum": "$total_price"}}}])
queryResult = [order for order in query]
print(queryResult)
# get avg price for all products
query = ordersCollection.aggregate([{"$group": {"_id": None, "total_price": {"$avg": "$total_price"}}}])
queryResult = [order for order in query]
print(queryResult)
# find using logical operator
query = ordersCollection.find({"$or": [{'total_price': 15.25}, {'product': {'$regex': 'G{1}'}}]}, {"_id": 0})
queryResult = [order for order in query]
print(queryResult)
# update all orders contains letter 'G'
query = ordersCollection.update_many({'product': {'$regex': 'G{1}'}}, {'$set': {'product': r"G"}})
print(query.modified_count)
query = ordersCollection.find({'product': {'$regex': '^G'}}, {"_id": 0})  # show them
queryResult = [order for order in query]
print(queryResult)
# delete a doc
query = ordersCollection.delete_one({'_id': 'id1'})
print("successfully done" if query.deleted_count else "failed")
query = ordersCollection.delete_one({'_id': 'id1'})
print("successfully done" if query.deleted_count else "failed")
# drop the collection
ordersCollection.drop()
