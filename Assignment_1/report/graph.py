import networkx as nx
from collections import namedtuple
import os

G = nx.DiGraph()

sni = "984392687152"

class Node(object):
    def __init__(self, uid):
        self.uid = uid
        self.color = "WHITE"
        self.pi = None

        self.d = None
        self.f = None

    def gen_label(self):

        t = ""

        if self.d:
            t += str(self.d) + "/"

        if self.f:
            t += str(self.f)

        return "{ " +str(self.uid) + " | " + str(t) + " }"


    def __hash__(self):
        return hash(self.uid)


for (u, v) in zip(sni[:-1], sni[1:]):
    G.add_edge(Node(u), Node(v))



def dump_graph(G, t):
    with open(f"{t}.graph", "w") as f:

        f.write("digraph G {\n")

        for u in G.nodes:
            # print("{} [fillcolor=\"{}\", style=\"filled\"]".format(u.uid, u.color.lower()))
            f.write("{} [fillcolor=\"{}\", style=\"filled\", shape=\"record\", label=\"{}\"]\n".format(
                u.uid,
                "yellow",
                u.gen_label()
                ))

        for (u, v) in G.edges:
            f.write("{h} -> {t}\n".format(
                h=u.uid,
                t=v.uid
            ))
        f.write("0\n")
        f.write("}\n")


    os.system(f"dot -Tpng {t}.graph > {t}.png")

# dump_graph(G)

time = 0

def dfs(G):
    for u in G.nodes:
        if u.color == "WHITE":
            dfs_visit(G, u)

def dfs_visit(G, u):
    global time
    time = time + 1
    dump_graph(G, time)
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

# nx.draw_networkx(G)
