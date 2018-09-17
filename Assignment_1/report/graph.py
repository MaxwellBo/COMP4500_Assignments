import networkx as nx
from collections import namedtuple

G = nx.DiGraph()

sni = "984392687152"

class Node(object):
    def __init__(self, uid):
        self.uid = uid
        self.color = "WHITE"
        self.pi = None

        self.d = None
        self.f = None

    def __hash__(self):
        return hash(self.uid)


for (u, v) in zip(sni[:-1], sni[1:]):
    G.add_edge(Node(u), Node(v))

def dump_graph(G):
    print("digraph G {")
    for (u, v) in G.edges:
        print("{h} [fillcolor={hf}] -> {t} [fillcolor={tf}]".format(
            h=u.uid,
            hf=u.color.lower(),
            t=v.uid,
            tf=v.color.lower()
        ))
    print("0")
    print("}")

time = 0

def dfs(G):
    for u in G.nodes:
        if u.color == "WHITE":
            dfs_visit(G, u)

def dfs_visit(G, u):
    global time
    time = time + 1
    u.d = time
    u.color = "GRAY"

    for v in G.successors(u):
        if v.color == "WHITE":
            v.pi = u
            dfs_visit(G, v)

    u.color = "BLACK"
    time = time + 1
    u.f = time

dfs(G)

dump_graph(G)


# nx.draw_networkx(G)
