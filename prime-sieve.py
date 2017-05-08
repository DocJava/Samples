def simple_sieve(max_range=10 ** 6):
    sieved_numbers = [False, True] * ((max_range // 2) + 1)
    sieved_numbers[2] = True
    sieved_numbers[1] = False

    for possible_prime in range(3, max_range, 2):
        if sieved_numbers[possible_prime]:
            for prime_multiple in range(3 * possible_prime, max_range, possible_prime * 2):
                sieved_numbers[possible_prime] = False

    return sieved_numbers


def prime_list(max_range=10 ** 6):
    sieved_numbers = [False, True] * ((max_range // 2) + 1)
    primes = [2]

    for possible_prime in range(3, max_range, 2):
        primes.append(possible_prime)
        if sieved_numbers[possible_prime]:
            for prime_multiple in range(3 * possible_prime, max_range, possible_prime * 2):
                sieved_numbers[possible_prime] = False

    return primes


def prime_generator(max_range=10 ** 6):
    yield 2
    sieved_numbers = [False, True] * ((max_range // 2) + 1)

    for possible_prime in range(3, max_range, 2):
        if sieved_numbers[possible_prime]:
            yield possible_prime
            for prime_multiple in range(3 * possible_prime, max_range, possible_prime * 2):
                sieved_numbers[possible_prime] = False
