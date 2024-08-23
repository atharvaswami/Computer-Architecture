package processor.memorysystem;
import generic.*;
import processor.*;
import configuration.Configuration;
import javax.swing.text.Element;

public class Cache {

    public int latency;
    Processor containingProcessor;
    int cache_size;
    boolean isPresent = true;
    CacheLine[] cachelines;
    int block_size;

    public Cache(Processor containingProcessor, int latency, int cache_size,int block_size)
    {
        this.latency=latency;
        this.cache_size=cache_size;
        this.containingProcessor=containingProcessor;
        this.cachelines = new CacheLine[cache_size];
        for(int i=0;i<cache_size;i++)
        {
            this.cachelines[i]=new CacheLine();
        }
        this.block_size=block_size;

    }
    public int getSize()
    {
        return cache_size;
    }
    public void addtoCache(int value)
    {
        //value=value-(value%block_size);
        //Calculate Set Number to check!
        int set_number=value;
        set_number=set_number-(set_number%8);
        set_number=set_number/8;
        set_number=set_number%(cache_size/2);

        int i;
        int done=0;
        //Check for valid bit
        for (i=set_number*2;i<=(set_number*2+1);i++)
        {
            cachelines[i].timestamp+=1;
            if ((cachelines[i].valid==0) && (done==0))
            {
                cachelines[i].tag=value-(value%block_size);
                cachelines[i].timestamp=0;
                cachelines[i].valid=1;
                done=1;
            }


        }
        //Choose index corresponding to max timestamp
        if(done==0)
        {
            int max_timestamp=0;
            int max_index=0;
            for(i=set_number*2;i<=(set_number*2+1);i++)
            {
                if(max_timestamp<cachelines[i].timestamp)
                {
                    max_timestamp=cachelines[i].timestamp;
                    max_index=i;
                }

            }
            cachelines[max_index].tag=value-(value%block_size);
            cachelines[max_index].timestamp=0;
            cachelines[max_index].valid=1;
        }
    }
    public boolean checkinCache(int value)
    {
        //value=value-(value%block_size);
        int set_number=value;
        set_number=set_number-(set_number%8);
        set_number=set_number/8;
        set_number=set_number%(cache_size/2);
        for (int i=0;i<cache_size;i++)
        {
            if(cachelines[i].valid==1)
            {
                if(cachelines[i].tag==value-(value%block_size))
                {
                    return true;
                }
            }
        }
        return false;
    }




}
