#pragma once
// I don't understant...
using namespace std;
struct commaFacet : public ctype<char> {
    mask my_table[table_size];
    public:
    commaFacet(size_t refs = 0) : ctype<char>(&my_table[0], false, refs)
    {
        copy_n(classic_table(), table_size, my_table);
        my_table[','] = (mask)space;
    }	
};