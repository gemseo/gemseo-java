
// Copyright 2021 IRT Saint Exupéry, https://www.irt-saintexupery.com
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License version 3 as published by the Free Software Foundation.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with this program; if not, write to the Free Software Foundation,
// Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

package com.irt.saintexupery.problems.sellar;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class Sellar2 extends SellarDiscipline {
    @Override
    public HashMap<String,Double[]> run(HashMap<String, Double[]> data) {
        HashMap<String, Double[]> result = new HashMap<>();
        Double content[] = {Math.sqrt(data.get("y_1")[0]) + data.get("x_shared")[0] + data.get("x_shared")[1]};
        result.put("y_2", content);
        return result;
    }

    @Override
    public  List<String> getInputDataNames(){
    	return Arrays.asList("y_1","x_shared");
    }
    @Override
    public  List<String> getOutputDataNames(){
    	return  Arrays.asList("y_2");
    }
}
