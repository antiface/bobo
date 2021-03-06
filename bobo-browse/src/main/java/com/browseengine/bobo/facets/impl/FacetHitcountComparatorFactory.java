/**
 * This software is licensed to you under the Apache License, Version 2.0 (the
 * "Apache License").
 *
 * LinkedIn's contributions are made under the Apache License. If you contribute
 * to the Software, the contributions will be deemed to have been made under the
 * Apache License, unless you expressly indicate otherwise. Please do not make any
 * contributions that would be inconsistent with the Apache License.
 *
 * You may obtain a copy of the Apache License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, this software
 * distributed under the Apache License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the Apache
 * License for the specific language governing permissions and limitations for the
 * software governed under the Apache License.
 *
 * © 2012 LinkedIn Corp. All Rights Reserved.  
 */

package com.browseengine.bobo.facets.impl;

import java.util.Comparator;

import com.browseengine.bobo.api.BrowseFacet;
import com.browseengine.bobo.api.ComparatorFactory;
import com.browseengine.bobo.api.FieldValueAccessor;
import com.browseengine.bobo.util.BigSegmentedArray;
import com.browseengine.bobo.util.IntBoundedPriorityQueue.IntComparator;

public class FacetHitcountComparatorFactory implements ComparatorFactory {
  public IntComparator newComparator(FieldValueAccessor valueList,
      final BigSegmentedArray counts) {
    
    return new IntComparator(){

      public int compare(Integer f1, Integer f2) {
        int val = counts.get(f1) - counts.get(f2);
        if (val==0)
        {
          val=f2-f1;
        }
        return val;
      }

      // use ploymorphism to avoid auto-boxing
      public int compare(int f1, int f2)
      {
        int val = counts.get(f1) - counts.get(f2);
        if (val==0)
        {
          val=f2-f1;
        }
        return val;
      }

    };
  }

  public static final Comparator<BrowseFacet> FACET_HITS_COMPARATOR = new Comparator<BrowseFacet>()
  {
    public int compare(BrowseFacet f1, BrowseFacet f2) {
      int val = f2.getHitCount() - f1.getHitCount();
      if (val==0)
      {
        val=f1.getValue().compareTo(f2.getValue());
      }
      return val;
    }		
  };

  public Comparator<BrowseFacet> newComparator() {
    return FACET_HITS_COMPARATOR;
  }
}
