from typing import List

def min_order(H: int, requests: List[int]) -> List[int]:
    sorted_requests = sorted(requests)

    midpoint = (sorted_requests[0] + sorted_requests[1]) / 2

    if H <= midpoint:
        return sorted_requests
    else:
        return sorted_requests[::-1] # reversed

def min_time(H: int, ordering: List[int]) -> int:
    seek_to_start = abs(ordering[0] - H)
    range = max(ordering) - min(ordering)

    return seek_to_start + range

def greedy_algo(H: int, requests: List[int]):
    minOrder = min_order(H, requests)
    minTime = min_time(H, minOrder)

    return minTime, minOrder
    
  
print(greedy_algo(200, [40, 180, 300, 10]))
